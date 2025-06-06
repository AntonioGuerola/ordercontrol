import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CategoriaUpdateService {
  private refreshSubject = new Subject<void>();
  refresh$ = this.refreshSubject.asObservable();

  notifyRefresh() {
    this.refreshSubject.next();
  }
}