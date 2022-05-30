import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseClient } from 'src/app/api-clients/course.client';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { CourseRp } from 'src/app/api-clients/model/course.model';
import { HomepageService } from 'src/app/share/homepage/homepage.service';
import { PageService } from 'src/app/share/page/page.service';

@Component({
  selector: 'app-coursetwomain',
  templateUrl: './coursetwomain.component.html',
  styleUrls: ['./coursetwomain.component.scss']
})
export class CoursetwomainComponent implements OnInit {
  public course_list:CourseRp[] =[];
  constructor(
    private _homepageService: HomepageService,
  ) {}
 
  ngOnInit(): void {
    
    this._homepageService.$data.subscribe(rp =>{
      this.course_list = rp
    })
  }

 

}