import { Component, OnInit } from '@angular/core';
import { UserRp } from 'src/app/api-clients/model/user.model';
import { UserClient } from 'src/app/api-clients/user.client';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  public profile: UserRp = new UserRp();

  constructor(private _userClient: UserClient) { }

  ngOnInit() {
    this.getData();
   }

  getData(): void{
    this._userClient.getMyProfile().subscribe(
      response => console.log(response) //this.profile = response.content
    )
  }

}
