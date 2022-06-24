import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Todo } from '../todo';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { BaseNewsService } from '../base-news.service';
import { environment as env } from '../../environments/environment';

@Injectable()
export class NewsService extends BaseNewsService {

  constructor(http: HttpClient) {
    super(http);
  }

  getAll(): Observable<Todo[]> {
    return this.http.get<any[]>(`${env.apiUrl}/news`, {headers: this.defaultHeaders}).pipe(
      map(body => body.map(n => Todo.fromObject(n)))
    );
  }

  create(headline: string, content: string): Observable<Todo> {
    return this.http.post<any>(`${env.apiUrl}/news`, {headline, content}, {headers: this.defaultHeaders}).pipe(
      map(body => Todo.fromObject(body))
    );
  }
}
