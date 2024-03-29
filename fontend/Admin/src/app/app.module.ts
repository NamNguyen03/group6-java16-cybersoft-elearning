
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { DashboardModule } from './components/dashboard/dashboard.module';
import { SharedModule } from './shared/shared.module';
import { CoursesModule } from './components/course/courses.module';
import { RolesModule } from './components/roles/roles.module';
import { GroupModule } from './components/group/group.module';
import { LessonsModule } from './components/lesson/lessons.module';
import { UsersModule } from './components/users/users.module';
import { SettingModule } from './components/setting/setting.module';
import { AuthModule } from './components/auth/auth.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { TokenInterceptor } from './shared/service/interceptor/token.interceptor';
import { ExceptionInterceptor } from './shared/service/interceptor/exception.interceptor';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { ToastrModule } from 'ngx-toastr';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { ProgramModule } from './components/program/program.module';
import { CommentModule } from './components/comment/comment.module';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule.withServerTransition({ appId: 'serverApp' }),
    ReactiveFormsModule,
    AppRoutingModule,
    FormsModule,
    DashboardModule,
    HttpClientModule,
    SettingModule,
    AuthModule,
    SharedModule,
    CommentModule,
    LessonsModule,
    CoursesModule,
    RolesModule,
    GroupModule,
    UsersModule,
    ProgramModule,
    Ng2SmartTableModule,
    AngularEditorModule,
    ToastrModule.forRoot()
  ],
  
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true,
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ExceptionInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
  
})

export class AppModule { }
