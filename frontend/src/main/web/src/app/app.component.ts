import {Component, OnInit} from '@angular/core';
import {ProjectService} from "./project.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  projects;
  title = 'web';

  constructor(private service: ProjectService) {
  }

  ngOnInit(): void {
    this.service.getProjects().subscribe(projects => {
      console.log(projects);
      this.projects = projects;
    })
  }

  selectProject(id: any): void {
    console.log('selected project', id);
  }
}
