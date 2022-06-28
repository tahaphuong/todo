export class Todo {
  id: number;
  publishedOn: Date;
  headline: string;
  content: string;
  author: string;
  isPrivate: boolean;

  static fromObject(object: any): Todo {
    const todo = new Todo();
    todo.id = object.id;
    todo.headline = object.headline;
    todo.content = object.content;
    todo.author = object.author;
    todo.isPrivate = object.isPrivate;
    todo.publishedOn  = new Date(object.publishedOn);
    return todo;
  }
}
