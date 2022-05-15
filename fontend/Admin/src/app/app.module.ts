import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { DashboardModule } from './components/dashboard/dashboard.module';
import { SharedModule } from './shared/shared.module';
import { ProductsModule } from './components/products/products.module';
import { SalesModule } from './components/sales/sales.module';
import { CoursesModule } from './components/course/courses.module';
import { RolesModule } from './components/roles/roles.module';
import { MediaModule } from './components/media/media.module';
import { GroupModule } from './components/group/group.module';
import { LessonsModule } from './components/lesson/lessons.module';
import { UsersModule } from './components/users/users.module';
import { LocalizationModule } from './components/localization/localization.module';
import { InvoiceModule } from './components/invoice/invoice.module';
import { SettingModule } from './components/setting/setting.module';;
import { ReportsModule } from './components/reports/reports.module';
import { AuthModule } from './components/auth/auth.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import { TokenInterceptor } from './shared/service/interceptor/token.interceptor';
import { ExceptionInterceptor } from './shared/service/interceptor/exception.interceptor';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { ToastrModule } from 'ngx-toastr';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { CourseDetailsComponent } from './components/course-details/course-details.component';
import { LessonInfoComponent } from './components/lesson-info/lesson-info.component';

@NgModule({
  declarations: [
    AppComponent,
    CourseDetailsComponent,
    LessonInfoComponent,
  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule.withServerTransition({ appId: 'serverApp' }),
    ReactiveFormsModule,
    AppRoutingModule,
    FormsModule,
    DashboardModule,
    InvoiceModule,
    HttpClientModule,
    SettingModule,
    ReportsModule,
    AuthModule,
    SharedModule,
    LocalizationModule,
    ProductsModule,
    SalesModule,
    LessonsModule,
    CoursesModule,
    RolesModule,
    MediaModule,
    GroupModule,
    UsersModule,
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
