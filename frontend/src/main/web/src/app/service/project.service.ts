import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Project} from "../model/project.model";

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private httpClient: HttpClient) {
  }

  getProjects(): Observable<Project[]> {
    return this.httpClient.get<Project[]>('http://localhost:8080/api/v2/projects');
  }


}



/*

update(user: User): Observable<ResponseWrapper> {
  return this.http.put(this.resourceUrl, user)
    .map((res: Response) => this.convertResponse(res));
}

find(login: string): Observable<User> {
  return this.http.get(`${this.resourceUrl}/${login}`).map((res: Response) => res.json());
}


private convertResponse(res: Response): ResponseWrapper {
  const jsonResponse = res.json();
  return new ResponseWrapper(res.headers, jsonResponse, res.status);
}
*/
