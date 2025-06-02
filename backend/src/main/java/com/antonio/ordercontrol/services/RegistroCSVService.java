package com.antonio.ordercontrol.services;

import com.antonio.ordercontrol.models.*;
import com.antonio.ordercontrol.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RegistroCSVService {

    @Autowired private RegistrosVentaRepository registrosVentaRepository;
    @Autowired private MesaRepository mesaRepository;
    @Autowired private ProductoRepository productoRepository;

    public void procesarCuenta(Cuenta cuenta) throws IOException {
        LocalDate hoy = LocalDate.now();
        String nombreArchivo = hoy + ".csv";
        Path ruta = Paths.get("registros", nombreArchivo);
        Files.createDirectories(ruta.getParent());

        // --- Generar CSV ---
        try (BufferedWriter writer = Files.newBufferedWriter(ruta, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write("Mesa,Hora Cobro,Producto,Cantidad,Precio Unitario,Total\n");

            List<Comandaproducto> todosLosProductos = cuenta.getIdMesa().getComandas().stream()
                    .flatMap(c -> c.getComandaproductos().stream())
                    .toList();

            for (Comandaproducto cp : todosLosProductos) {
                BigDecimal total = cp.getPrecioUnitario().multiply(BigDecimal.valueOf(cp.getCantidad()));
                writer.write(String.format("%s,%s,%s,%d,%.2f,%.2f\n",
                        cuenta.getIdMesa().getNumMesa(),
                        cuenta.getHoraCobro(),
                        cp.getProducto().getNombre(),
                        cp.getCantidad(),
                        cp.getPrecioUnitario(),
                        total));
            }

            writer.write("\n--- ANÁLISIS ---\n");

            // Mesa más usada (por cantidad de comandas)
            List<Mesa> todasMesas = mesaRepository.findAll();
            int maxUso = todasMesas.stream()
                    .mapToInt(m -> m.getComandas().size())
                    .max().orElse(0);

            List<Mesa> mesasMasUsadas = todasMesas.stream()
                    .filter(m -> m.getComandas().size() == maxUso)
                    .toList();

            writer.write("Mesa(s) más usada(s):\n");
            for (Mesa m : mesasMasUsadas) {
                writer.write("- Mesa " + m.getNumMesa() + " (" + m.getTipo().getNombre() + ") con " + m.getComandas().size() + " comandas\n");
            }

            // Top 3 productos por categoría
            List<Producto> productos = productoRepository.findAll();

            Map<String, Map<String, Integer>> productosPorCategoria = new HashMap<>();

            for (Producto p : productos) {
                int cantidadTotal = p.getComandaproductos().stream()
                        .mapToInt(Comandaproducto::getCantidad)
                        .sum();

                String categoria = p.getCategoria().getNombre();
                productosPorCategoria.putIfAbsent(categoria, new HashMap<>());
                productosPorCategoria.get(categoria).put(p.getNombre(), cantidadTotal);
            }

            writer.write("\nTop 3 productos por categoría:\n");

            for (String categoria : productosPorCategoria.keySet()) {
                writer.write("Categoría: " + categoria + "\n");

                productosPorCategoria.get(categoria).entrySet().stream()
                        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                        .limit(3)
                        .forEach(entry -> {
                            try {
                                writer.write("- " + entry.getKey() + ": " + entry.getValue() + " unidades\n");
                            } catch (IOException e) {
                                throw new UncheckedIOException(e);
                            }
                        });

                writer.write("\n");
            }
        }

        // --- Actualizar registros globales ---
        actualizarRegistroVenta(TipoPeriodo.DIARIO, hoy, hoy, cuenta.getSumaTotal());
        actualizarRegistroVenta(TipoPeriodo.SEMANAL, hoy.with(DayOfWeek.MONDAY), hoy.with(DayOfWeek.SUNDAY), cuenta.getSumaTotal());
        actualizarRegistroVenta(TipoPeriodo.MENSUAL, hoy.withDayOfMonth(1), hoy.withDayOfMonth(hoy.lengthOfMonth()), cuenta.getSumaTotal());
        actualizarRegistroVenta(TipoPeriodo.ANUAL, hoy.withDayOfYear(1), hoy.withDayOfYear(hoy.lengthOfYear()), cuenta.getSumaTotal());
    }

    private void actualizarRegistroVenta(TipoPeriodo tipo, LocalDate inicio, LocalDate fin, BigDecimal monto) {    List<RegistrosVenta> existentes = registrosVentaRepository.findByFechaDentroDelRango(inicio);
        RegistrosVenta rv;
        if (!existentes.isEmpty()) {
            rv = existentes.get(0);
        } else {
            rv = new RegistrosVenta();
            rv.setTipoPeriodo(tipo.name());
            rv.setFechaInicio(inicio);
            rv.setFechaFin(fin);
            rv.setMontoTotal(BigDecimal.ZERO);
        }

        rv.setMontoTotal(rv.getMontoTotal().add(monto));
        registrosVentaRepository.save(rv);

    }
}
