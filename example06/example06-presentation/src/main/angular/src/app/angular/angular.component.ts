import { Component, OnInit } from '@angular/core';
import { NewsService } from './news.service';
import { Todo } from '../todo';

@Component({
  selector: 'wt2-angular',
  templateUrl: './angular.component.html',
  styleUrls: ['./angular.component.sass'],
  providers: [NewsService]
})
export class AngularComponent implements OnInit {

  public latest: Todo;
  public news: Todo[] = [];

  constructor(protected newsService: NewsService) {
  }

  ngOnInit() {
    this.load();
  }

  load(): void {
    this.newsService.getAll().subscribe({
      next: news => this.news = news,
      error: console.error
    });
  }
}
