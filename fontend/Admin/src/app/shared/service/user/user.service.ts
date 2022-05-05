import { UserClient } from 'src/app/api-clients/user.client';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import jwt_decode from 'jwt-decode';
import { UserRp } from 'src/app/api-clients/model/user.model';
import * as e from 'express';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private _$userCurrent: BehaviorSubject<UserRp> = new BehaviorSubject(new UserRp());
  public readonly $userCurrent: Observable<UserRp> = this._$userCurrent.asObservable();

  constructor(private _router: Router) { 
    if(!this.getTokenRemainingTime()){
      this._router.navigate(['auth/login']);
    }
   
  }

  public setJWT(jwt: string): void{
    localStorage.setItem('jwt', jwt);
  }

  public setUserCurrent(user: UserRp): void{
    this._$userCurrent.next(user);
  }

  private _getDecodedJwt(jwt: string): {username: string, displayName: string, exp: number} | null {
    try {
      return jwt_decode(jwt);
    } catch (Error) {
      return null;
    }
  }

  getTokenRemainingTime(): boolean {
    let jwt = localStorage.getItem('jwt');
    if(jwt != null && jwt != undefined) {
      let exp = this._getDecodedJwt(jwt)?.exp;
      if(exp != null){
        let expires = new Date(exp*1000);
        return (expires.getTime() - Date.now()) > 0;
      }
    }
    return false;
  }

  getJWT(): string{
    let jwt = localStorage.getItem('jwt');
    return jwt  == null ? '' : jwt ;
  }
}
