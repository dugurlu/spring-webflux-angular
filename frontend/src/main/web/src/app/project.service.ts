import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  constructor(private httpClient: HttpClient) {
  }

  getProjects(): Observable<any> {
    return this.httpClient.get('http://localhost:8080/api/v2/projects');
  }
}
