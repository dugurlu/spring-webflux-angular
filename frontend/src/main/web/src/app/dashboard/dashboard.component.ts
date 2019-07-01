import {Component, OnInit} from '@angular/core';
import {Project} from "../model/project.model";
import {ProjectService} from "../service/project.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  projects: Project[];
  selectedProject: Project;
  title = 'web';

  constructor(private service: ProjectService) {
  }

  ngOnInit(): void {
    this.service.getProjects().subscribe(projects => {
      console.log(projects);
      this.projects = projects;
    })
  }

  selectProject(id: string): void {
    this.selectedProject = this.projects.find(project => project.id === id);
    console.log('selected project', this.selectedProject);
  }

}
