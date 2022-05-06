import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Router } from '@angular/router';
import { UserRp } from 'src/app/api-clients/model/user.model';
import { NavService } from '../../service/nav.service';
import { UserService } from '../../service/user/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  public right_sidebar: boolean = false;
  public open: boolean = false;
  public openNav: boolean = false;
  public userCurrent: UserRp;
  public isOpenMobile : boolean;

  @Output() rightSidebarEvent = new EventEmitter<boolean>();

  constructor(public navServices: NavService, private _userService: UserService,  private router: Router,) { }

  collapseSidebar() {
    this.open = !this.open;
    this.navServices.collapseSidebar = !this.navServices.collapseSidebar
  }
  right_side_bar() {
    this.right_sidebar = !this.right_sidebar
    this.rightSidebarEvent.emit(this.right_sidebar)
  }

  openMobileNav() {
    this.openNav = !this.openNav;
  }
  
  logout(){
    this._userService.logout();
    this.router.navigate(['auth/login']);
  }

  ngOnInit() { 
    this._userService.$userCurrent.subscribe(user => this.userCurrent = user);
   }

}
