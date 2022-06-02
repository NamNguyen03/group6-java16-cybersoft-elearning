import { Component, OnInit } from '@angular/core';
import { CourseRp } from 'src/app/api-clients/model/course.model';
import { HomepageService } from 'src/app/share/homepage/homepage.service';

@Component({
  selector: 'app-coursetwomain',
  templateUrl: './coursetwomain.component.html',
  styleUrls: ['./coursetwomain.component.scss']
})
export class CoursetwomainComponent implements OnInit {
  public course_list:CourseRp[] =[];
 
  constructor(
    private _homepageService: HomepageService,
   

  ) {
    }
 
  ngOnInit(): void {
    
    this._homepageService.$data.subscribe(rp =>{
      this.course_list = rp
    })
  }

 

}