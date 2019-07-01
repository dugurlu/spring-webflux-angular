import {Rating} from "./rating.model";


export class Project {
  constructor(
    public id: string,
    public name: string,
    public customer: string,
    public ratings: Rating[]
  ) {}
}
