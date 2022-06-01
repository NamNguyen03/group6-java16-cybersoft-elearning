import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { PageRequest } from 'src/app/api-clients/model/common.model';
import { UserRp } from 'src/app/api-clients/model/user.model';
import { UserClient } from 'src/app/api-clients/user.client';

@Component({
  selector: 'app-instructormain',
  templateUrl: './instructormain.component.html',
  styleUrls: ['./instructormain.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class InstructormainComponent implements OnInit {
  public userAll: UserRp[] = [];
  constructor(private userClient: UserClient) { }

  ngOnInit(): void {
    this.getAllInstructor();
  }

  getAllInstructor(): void {
    this.userClient.searchRequest(new PageRequest(1, 6, 0, 0, [], [])).subscribe(
      response => {
        this.userAll = response.content.items || [];
      }
    )
  }

}
