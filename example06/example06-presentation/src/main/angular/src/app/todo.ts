export class Todo {
  publishedOn: Date;
  headline: string;
  content: string;

  static fromObject(object: any): Todo {
    const n = new Todo();
    n.headline = object.headline;
    n.content = object.content;
    n.publishedOn  = new Date(object.publishedOn);
    return n;
  }
}
