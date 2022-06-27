import { Component, Input } from '@angular/core';
import { Todo } from '../../todo';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'wt2-news-details',
  templateUrl: './news-details.component.html',
  styleUrls: ['./news-details.component.sass']
})
export class NewsDetailsComponent {

  @Input()
  public news: Todo;

  @Input()
  public allowHtmlContent: boolean;

  public editMode = false;
  public headline: string;
  public content: string;

  constructor(private domSanitizer: DomSanitizer) {
    this.allowHtmlContent = false;
  }

  getTrustedHtml(value: string): SafeHtml {
    return this.domSanitizer.bypassSecurityTrustHtml(value);
  }

  //PUT
  setInputValue() {
    editMode = true;
    headline = news.headline
    content = news.content
  }

  saveItem() {

  }

  // DELETE
  deleteItem() {

  }
}
