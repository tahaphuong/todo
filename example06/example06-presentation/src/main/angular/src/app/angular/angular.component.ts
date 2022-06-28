import { Component, OnInit } from '@angular/core';
import { TodoService } from './todo.service';
import { Todo } from '../todo';

@Component({
  selector: 'wt2-angular',
  templateUrl: './angular.component.html',
  styleUrls: ['./angular.component.sass'],
  providers: [TodoService]
})
export class AngularComponent implements OnInit {

  public todos: Todo[] = [];
  isAuthenticated = false;

  constructor(protected todoService: TodoService) {
  }

  ngOnInit() {
    this.load();
  }

  load(): void {
    this.todoService.getAll().subscribe({
      next: items => this.todos = items,
      error: console.error
    });
  }

  handleAuthed(authed: boolean) {
    this.isAuthenticated = authed;
    if (!authed) {
      this.load();
    }
  }
}
