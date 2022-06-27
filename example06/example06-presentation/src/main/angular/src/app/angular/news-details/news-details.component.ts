import { Component, Input } from '@angular/core';
import { Todo } from '../../todo';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { AuthNewsService } from '../../auth/auth-news.service'

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
  public headline: string = "";
  public content: string = "";
  public errorMessage: string;

  constructor(private domSanitizer: DomSanitizer, private authNewsService: AuthNewsService) {
    this.allowHtmlContent = false;
  }

  getTrustedHtml(value: string): SafeHtml {
    return this.domSanitizer.bypassSecurityTrustHtml(value);
  }

  //PUT
  public setInputValue(): void{}

  saveItem():void {
        if (this.headline.trim() != null && this.content.trim() != null) {
                this.authNewsService.update(this.news.id, this.headline, this.content).subscribe({
                  error: () => this.errorMessage = 'Could not updated todo'
                });
                console.log("updated news-details")
        }
  }

  // DELETE
  deleteItem():void {
    this.authNewsService.remove(this.news.id).subscribe({
        error: () => this.errorMessage = "Could not delete todo"
    });
  }
}

//     @Output()
//     public created = new EventEmitter();

//
//     constructor(private newsService: NewsService) { }
//
//     public createNews(e: Event): void {
//       console.log("creating")
//       e.preventDefault();
//       this.errorMessage = null;
//
//       if (this.headline.trim() != null && this.content.trim() != null) {
//         this.newsService.create(this.headline, this.content).subscribe({
//           next: () => {
//             this.created.emit();
//             this.headline = '';
//             this.content = '';
//           },
//           error: () => this.errorMessage = 'Could not create news'
//         });
//       }
//     }
//
//     getCharsLeft(): number {
//       return 255 - this.content.length;
//     }
//
//     get canCreate(): boolean {
//       return this.getCharsLeft() > 0 && this.headline.trim() !== '';
//     }

