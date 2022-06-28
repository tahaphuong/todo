import {Component, EventEmitter, Input, Output} from '@angular/core';
import { TodoService } from '../todo.service';
import {AuthTodoService} from "../../auth/auth-todo.service";

@Component({
  selector: 'wt2-create-todo',
  templateUrl: './create-todo.component.html',
  styleUrls: ['./create-todo.component.sass']
})
export class CreateTodoComponent {

  @Output()
  public created = new EventEmitter();

  @Input()
  public getUsername;

  public headline: string = "";
  public content: string = "";
  public isPrivate: boolean = false;
  public errorMessage: string;

  constructor(private authTodoService: AuthTodoService) { }

  public createNews(e: Event): void {
    e.preventDefault();
    this.errorMessage = null;

    if (this.headline.trim() != null && this.content.trim() != null) {
      this.authTodoService.create(this.headline, this.content, this.isPrivate, this.getUsername).subscribe({
        next: () => {
          this.created.emit();
          this.headline = "";
          this.content = "";
          this.isPrivate = false;
        },
        error: () => this.errorMessage = 'Could not create news'
      });
    }
  }

  getCharsLeft(): number {
    return 255 - this.content.length;
  }

  get canCreate(): boolean {
    return this.getCharsLeft() > 0 && this.headline.trim() !== '';
  }
}
