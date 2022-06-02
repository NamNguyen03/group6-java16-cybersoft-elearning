import { Injectable, HostListener, Inject } from '@angular/core';
import { BehaviorSubject, Observable, Subscriber } from 'rxjs';
import { WINDOW } from "./windows.service";
// Menu
export interface Menu {
	path?: string;
	title?: string;
	icon?: string;
	type?: string;
	badgeType?: string;
	badgeValue?: string;
	active?: boolean;
	bookmark?: boolean;
	children?: Menu[];
}

@Injectable({
	providedIn: 'root'
})

export class NavService {

	public screenWidth: any
	public collapseSidebar: boolean = false

	constructor(@Inject(WINDOW) private window) {
		this.onResize();
		if (this.screenWidth < 991) {
			this.collapseSidebar = true
		}
	}

	// Windows width
	@HostListener("window:resize", ['$event'])
	onResize(event?) {
		this.screenWidth = window.innerWidth;
	}

	MENUITEMS: Menu[] = [
		{
			path: '/dashboard/default', title: 'Dashboard', icon: 'home', type: 'link', badgeType: 'primary', active: false
		},
		{
			title: 'Program', icon: 'dollar-sign', type: 'sub', active: false, children: [
				{ path: '/programs/create-program', title: 'Create Program', type: 'link' },
				{ path: '/programs/list-program', title: 'List Program', type: 'link' },
			]
		},
		{
			title: 'Courses', icon: 'tag', type: 'sub', active: false, children: [
				{ path: '/courses/list-course', title: 'List Courses', type: 'link' },
				{ path: '/courses/create-course', title: 'Create Courses', type: 'link' },
			]
		},
		{
			title: 'Role', icon: 'clipboard', type: 'sub', active: false, children: [
				{ path: '/roles/list-role', title: 'List Role', type: 'link' },
				{ path: '/roles/create-role', title: 'Create Role', type: 'link' },
			]
		},
		{
			title: 'Group', icon: 'align-left', type: 'sub', active: false, children: [
				{ path: '/groups/list-group', title: 'Group List', type: 'link' },
				{ path: '/groups/create-group', title: 'Create Group', type: 'link' },
			]
		},
		{
			title: 'Users', icon: 'user-plus', type: 'sub', active: false, children: [
				{ path: '/users/list-user', title: 'User List', type: 'link' },
				{ path: '/users/create-user', title: 'Create User', type: 'link' },
			]
		},
		{
			title: 'Comment', icon: 'users', type: 'sub', active: false, children: [
				{ path: '/comments/list-status-comment', title: 'List Status Comment', type: 'link' },
			]
		},
		{
			title: 'Settings', icon: 'settings', type: 'sub', children: [
				{ path: '/settings/profile', title: 'Profile', type: 'link' },
			]
		},
		{
			title: 'Login',path: '/auth/login', icon: 'log-in', type: 'link', active: false
		}
	]
	items = new BehaviorSubject<Menu[]>(this.MENUITEMS);


}
