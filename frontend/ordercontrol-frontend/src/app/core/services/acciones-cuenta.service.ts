import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { saveAs } from 'file-saver';
import { jsPDF } from 'jspdf';
import autoTable from 'jspdf-autotable';
import { Observable } from 'rxjs';
import { Producto } from '../models/producto';

@Injectable({
  providedIn: 'root',
})
export class AccionesCuentaService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  cobrarCuenta(idMesa: number): Observable<void> {
    return new Observable((observer) => {
      this.http
        .post(`${this.apiUrl}/cuentas/generar/mesa/${idMesa}`, {})
        .subscribe({
          next: (cuenta: any) => {
            if (!cuenta || !cuenta.productos) {
              observer.error(new Error('Cuenta inválida o vacía.'));
              return;
            }

            const csvData = this.convertirCuentaACSV(cuenta);
            const blob = new Blob([csvData], {
              type: 'text/csv;charset=utf-8;',
            });
            const fecha = new Date().toISOString().split('T')[0];
            saveAs(blob, `${fecha}.csv`);

            this.http
              .put(`${this.apiUrl}/mesas/${idMesa}/liberar`, {})
              .subscribe({
                next: () => {
                  observer.next();
                  observer.complete();
                },
                error: (err) => observer.error(err),
              });
          },
          error: (err) => observer.error(err),
        });
    });
  }

  imprimirCuenta(idMesa: number) {
    this.http
      .get<any>(`${this.apiUrl}/cuentas/mesa/${idMesa}/generar-temporal`)
      .subscribe({
        next: (cuenta) => {
          const doc = new jsPDF();
          const fecha = new Date();
          const horaCobro = fecha.toLocaleTimeString('es-ES', {
            hour: '2-digit',
            minute: '2-digit',
          });

          const productos = cuenta.productos.map((p: any) => {
            const cantidad = p.cantidad ?? 0;
            const nombre = p.nombre ?? '';
            const precioUnitario = p.precio ?? 0;
            const total = (precioUnitario * cantidad).toFixed(2);

            return [cantidad, nombre, precioUnitario.toFixed(2), total];
          });

          const totalSinIVA = cuenta.productos.reduce((acc: number, p: any) => {
            const cantidad = p.cantidad ?? 0;
            const precioUnitario = p.precio ?? 0;
            return acc + precioUnitario * cantidad;
          }, 0);

          const totalConIVA = (totalSinIVA * 1.21).toFixed(2);

          doc.setFontSize(16);
          doc.text(
            `Cuenta de la Mesa ${cuenta.tipoMesa ?? ''} ${
              cuenta.numMesa ?? cuenta.idMesa ?? ''
            }`,
            10,
            10
          );

          doc.setFontSize(12);
          doc.text(`Hora de cobro: ${horaCobro}`, 10, 20);

          autoTable(doc, {
            startY: 30,
            head: [['Cantidad', 'Producto', 'Precio Unitario', 'Total']],
            body: productos,
          });

          const finalY = (doc as any).lastAutoTable.finalY || 50;
          doc.text(
            `Total sin IVA: ${totalSinIVA.toFixed(2)} €`,
            140,
            finalY + 10
          );
          doc.text(`Total con IVA (21%): ${totalConIVA} €`, 140, finalY + 20);

          doc.save(
            `Cuenta_Mesa_${
              cuenta.numMesa ?? cuenta.idMesa ?? 'desconocida'
            }.pdf`
          );
        },
        error: (err) => {
          alert(
            err?.error?.message || 'Error al generar la cuenta para imprimir.'
          );
        },
      });
  }

  anularCuenta(idMesa: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/mesas/anular/${idMesa}`);
  }

  private convertirCuentaACSV(cuenta: any): string {
    let csv = 'Hora Cobro, Monto Total, Método de Pago\n';
    const hora = cuenta?.horaCobro || '';
    const monto = cuenta?.sumaTotal?.toString().replace(',', '.') || '0.00';
    const metodo = cuenta?.metodoPago || '';

    csv += `${hora}, ${monto}, ${metodo}\n\n`;
    csv += 'Producto, Precio Unitario, Cantidad, Total\n';

    (cuenta?.productos || []).forEach((producto: any) => {
      if (!producto) return;

      const nombre = producto.nombre || '';
      const precioUnitario = parseFloat(
        producto.precio?.toString().replace(',', '.') || '0'
      );
      const cantidad = producto.cantidad || 0;
      const total = (precioUnitario * cantidad).toFixed(2);

      csv += `${nombre}, ${precioUnitario}, ${cantidad}, ${total}\n`;
    });

    return csv;
  }

  obtenerOCrearComanda(idMesa: number): Observable<any> {
    return this.http.get(
      `${this.apiUrl}/comandas/mesa/${idMesa}/obtener-o-crear`
    );
  }

  agregarProductoAComanda(
    idComanda: number,
    producto: Producto
  ): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/comandaproductos/comanda/${idComanda}/producto`,
      {
        idProducto: producto.id,
        cantidad: producto.cantidad,
        precioUnitario: producto.precio,
      }
    );
  }
}
