import { keyframes } from '@angular/animations';
import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseClient } from 'src/app/api-clients/course.client';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { CourseRp } from 'src/app/api-clients/model/course.model';
import { HomepageService } from 'src/app/share/homepage/homepage.service';

@Component({
  selector: 'app-coursebar',
  templateUrl: './coursebar.component.html',
  styleUrls: ['./coursebar.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class CoursebarComponent implements OnInit {
  public valueFieldNameSearch = '';
  
  constructor(
    private _homePageService: HomepageService
    
  ) {
    
  }

  ngOnInit(): void {
  }

  changeInputSearch(event:any){
    if(event.keyCode == 13){
      this._homePageService.setValueSearch(this.valueFieldNameSearch);
    }
  }
}
