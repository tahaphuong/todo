import { Component, Input } from '@angular/core';
import { Todo } from '../../todo';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'wt2-news-list',
  templateUrl: './news-list.component.html',
  styleUrls: ['./news-list.component.sass']
})
export class NewsListComponent {

  @Input()
  public news: Todo[] = [];

  @Input()
  public allowHtmlContent: boolean;

  public selectedTodo: Todo

  constructor(private domSanitizer: DomSanitizer) {
    this.allowHtmlContent = false;
  }

  get reversedNews(): Todo[] {
    return this.news.slice().reverse();
  }

  onSelect(todo : Todo) {
    if (this.selectedTodo === todo) {
      this.selectedTodo = null;
    }
    this.selectedTodo = todo;
  }

  getTrustedHtml(value: string): SafeHtml {
    return this.domSanitizer.bypassSecurityTrustHtml(value);
  }
}
