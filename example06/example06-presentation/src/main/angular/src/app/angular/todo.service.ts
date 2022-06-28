import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Todo } from '../todo';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { BaseTodoService } from '../base-todo.service';
import { environment as env } from '../../environments/environment';

@Injectable()
export class TodoService extends BaseTodoService {

  constructor(http: HttpClient) {
    super(http);
  }

  getAll(): Observable<Todo[]> {
    return this.http.get<any[]>(`${env.apiUrl}/todo`, {headers: this.defaultHeaders}).pipe(
      map(body => body.map(n => Todo.fromObject(n)))
    );
  }
}
