import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { saveAs } from 'file-saver';
import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';
import { Observable } from 'rxjs';

pdfMake.vfs = pdfMake.vfs;

@Injectable({
  providedIn: 'root'
})
export class AccionesCuentaService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  cobrarCuenta(idMesa: number): Observable<void> {
    return new Observable(observer => {
      this.http.post(`${this.apiUrl}/cuentas/generar/mesa/${idMesa}`, {}).subscribe((cuenta: any) => {
        // Generar y guardar el CSV
        const csvData = this.convertirCuentaACSV(cuenta);
        const blob = new Blob([csvData], { type: 'text/csv;charset=utf-8;' });
        const fecha = new Date().toISOString().split('T')[0];
        saveAs(blob, `${fecha}.csv`);

        // Eliminar y recrear la mesa
        this.http.delete(`${this.apiUrl}/mesas/${idMesa}`).subscribe(() => {
          this.http.post(`${this.apiUrl}/mesas`, {
            numero: cuenta.idMesa,
            tipo: cuenta.tipoMesa
          }).subscribe(() => {
            observer.next();
            observer.complete();
          });
        });
      });
    });
  }

  imprimirCuenta(idMesa: number) {
    this.http.get<any[]>(`${this.apiUrl}/cuentas/mesa/${idMesa}`).subscribe((cuentas) => {
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
    csv += `${cuenta.horaCobro}, ${cuenta.sumaTotal}, ${cuenta.metodoPago}\n\n`;
    csv += 'Producto, Precio Unitario, Cantidad, Total\n';
    cuenta.productos.forEach((producto: any) => {
      const total = producto.precioUnitario * producto.cantidad;
      csv += `${producto.nombre}, ${producto.precioUnitario}, ${producto.cantidad}, ${total}\n`;
    });
    return csv;
  }

  private generarPDF(cuenta: any): any {
    const productos = cuenta.productos.map((producto: any) => {
      return [producto.nombre, producto.precioUnitario, producto.cantidad, producto.precioUnitario * producto.cantidad];
    });

    return {
      content: [
        { text: `Cuenta de la Mesa ${cuenta.tipoMesa} ${cuenta.idMesa}`, style: 'header' },
        { text: `Hora de Cobro: ${cuenta.horaCobro}` },
        { text: `Monto Total: ${cuenta.sumaTotal}` },
        { text: `Método de Pago: ${cuenta.metodoPago}` },
        {
          table: {
            widths: ['*', '*', '*', '*'],
            body: [
              ['Producto', 'Precio Unitario', 'Cantidad', 'Total'],
              ...productos
            ]
          }
        }
      ],
      styles: {
        header: {
          fontSize: 18,
          bold: true
        }
      }
    };
  }
}
