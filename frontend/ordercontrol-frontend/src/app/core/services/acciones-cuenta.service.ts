import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { saveAs } from 'file-saver';
import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';
import { Observable } from 'rxjs';
import { Producto } from '../models/producto';

pdfMake.vfs = pdfMake.vfs;

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
                error: (err) => {
                  observer.error(err);
                },
              });
          },
          error: (err) => {
            observer.error(err);
          },
        });
    });
  }

  imprimirCuenta(idMesa: number) {
    this.http
      .get<any[]>(`${this.apiUrl}/cuentas/mesa/${idMesa}`)
      .subscribe((cuentas) => {
        const cuenta = cuentas[cuentas.length - 1];
        const docDefinition = this.generarPDF(cuenta);
        const nombreArchivo = `${cuenta.tipoMesa}_${cuenta.idMesa}.pdf`;
        pdfMake.createPdf(docDefinition).download(nombreArchivo);
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

  private generarPDF(cuenta: any): any {
    const productos = cuenta.productos.map((producto: any) => {
      return [
        producto.nombre,
        producto.precioUnitario,
        producto.cantidad,
        producto.precioUnitario * producto.cantidad,
      ];
    });

    return {
      content: [
        {
          text: `Cuenta de la Mesa ${cuenta.tipoMesa} ${cuenta.idMesa}`,
          style: 'header',
        },
        { text: `Hora de Cobro: ${cuenta.horaCobro}` },
        { text: `Monto Total: ${cuenta.sumaTotal}` },
        { text: `Método de Pago: ${cuenta.metodoPago}` },
        {
          table: {
            widths: ['*', '*', '*', '*'],
            body: [
              ['Producto', 'Precio Unitario', 'Cantidad', 'Total'],
              ...productos,
            ],
          },
        },
      ],
      styles: {
        header: {
          fontSize: 18,
          bold: true,
        },
      },
    };
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
