import { Injectable } from '@angular/core';
import { BaseNewsService } from '../base-news.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Todo } from '../todo';
import { map } from 'rxjs/operators';
import { BasicAuthService } from './basic-auth.service';
import { AuthService } from './auth.service';

@Injectable()
export class AuthNewsService extends BaseNewsService {

  private _authService: AuthService;

  constructor(http: HttpClient) {
    super(http);
    this._authService = new BasicAuthService(http);
  }

  getAll(): Observable<Todo[]> {
    return this.http.get<any[]>(`${this._authService.getBaseUrl()}/news`, {headers: this._authService.getAuthHeaders()}).pipe(
      map(body => body.map(n => Todo.fromObject(n)))
    );
  }

  getNewest(): Observable<Todo> {
    return this.http.get<any>(`${this._authService.getBaseUrl()}/news/newest`, {headers: this._authService.getAuthHeaders()}).pipe(
      map(body => Todo.fromObject(body))
    );
  }

  create(headline: string, content: string): Observable<Todo> {
    return this.http.post<any>(`${this._authService.getBaseUrl()}/news`, {headline, content}, {headers: this._authService.getAuthHeaders()}).pipe(
      map(body => Todo.fromObject(body))
    );
  }

  update(id: number, headline: string, content: string): Observable<Todo>{
  console.log("updated auth-news")
    return this.http.put<any>(`${this._authService.getBaseUrl()}/todos/${id}`, {headline, content}, {headers: this._authService.getAuthHeaders()}).pipe(
      map(body => Todo.fromObject(body))
    );
  }

  remove(id: number): Observable<Todo>{
    return this.http.delete<any>(`${this._authService.getBaseUrl()}/todos/${id}`, {headers: this._authService.getAuthHeaders()}).pipe(
      map(body => Todo.fromObject(body))
    );
  }

  set authService(value: AuthService) {
    this._authService = value;
  }
}
