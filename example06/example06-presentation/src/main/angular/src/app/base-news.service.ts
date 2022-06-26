import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Todo } from './todo';

export abstract class BaseNewsService {
  protected defaultHeaders = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  protected constructor(protected http: HttpClient) {
  }

  abstract getAll(): Observable<Todo[]>;

  abstract create(headline: string, content: string): Observable<Todo>;
}
