export interface IParents {
  id?: number;
}

export class Parents implements IParents {
  constructor(public id?: number) {}
}
