export class Todo {
  id: number;
  publishedOn: Date;
  headline: string;
  content: string;

  static fromObject(object: any): Todo {
    const n = new Todo();
    n.id = object.id;
    n.headline = object.headline;
    n.content = object.content;
    n.publishedOn  = new Date(object.publishedOn);
    return n;
  }
}
