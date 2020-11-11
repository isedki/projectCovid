export interface INews {
  id?: number;
  title?: string;
}

export class News implements INews {
  constructor(public id?: number, public title?: string) {}
}
