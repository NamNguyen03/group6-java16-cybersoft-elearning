import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private _$displayName: BehaviorSubject<string> = new BehaviorSubject('');
  public readonly $displayName: Observable<string> = this._$displayName.asObservable();
  private _$isLogin: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  public readonly $isLogin: Observable<boolean> = this._$isLogin.asObservable();

  constructor() { 
    this._getDisplayName();
    this.getTokenRemainingTime();
    this.$displayName.subscribe(displayName => {
      this._$isLogin.next('' != displayName );
    });
  }

  public setJWT(jwt: string, displayName: string): void{
    localStorage.setItem('jwt', jwt);
    this.setDisplayName(displayName);
  }

  public setDisplayName(displayName: string): void{
    this._$displayName.next(displayName);
    localStorage.setItem('displayName', displayName);
  }

  private _getDisplayName(): void {
    let displayName = localStorage.getItem('displayName');
    
    if(!this.getTokenRemainingTime()){
      localStorage.setItem('displayName', '');
      this._$displayName.next('');
    }

    if('' != displayName && displayName != null && displayName != undefined) { 
      this._$displayName.next(displayName);
    }
   
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
