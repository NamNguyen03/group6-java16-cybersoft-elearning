import { NgModule } from '@angular/core';
import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';
import { AboutmainComponent } from './eduman/about/aboutmain/aboutmain.component';
import { BlogmainComponent } from './eduman/blog/blogmain/blogmain.component';
import { BlogdetailsmainComponent } from './eduman/blogdetails/blogdetailsmain/blogdetailsmain.component';
import { CartmainComponent } from './eduman/cart/cartmain/cartmain.component';
import { CheckoutmainComponent } from './eduman/checkout/checkoutmain/checkoutmain.component';
import { ContactmainComponent } from './eduman/contact/contactmain/contactmain.component';
import { CoursefourmainComponent } from './eduman/course/coursefour/coursefourmain/coursefourmain.component';
import { CourseonemainComponent } from './eduman/course/courseone/courseonemain/courseonemain.component';
import { CoursethreemainComponent } from './eduman/course/coursethree/coursethreemain/coursethreemain.component';
import { CoursetwomainComponent } from './eduman/course/coursetwo/coursetwomain/coursetwomain.component';
import { CoursedetailsmainComponent } from './eduman/coursedetails/coursedetailsmain/coursedetailsmain.component';
import { ErrorpageComponent } from './eduman/errorpage/errorpage.component';
import { EventmainComponent } from './eduman/event/eventmain/eventmain.component';
import { EventdetailsmainComponent } from './eduman/eventdetails/eventdetailsmain/eventdetailsmain.component';
import { FaqdetailsmainComponent } from './eduman/faqdetails/faqdetailsmain/faqdetailsmain.component';
import { FaqmainComponent } from './eduman/faqpage/faqmain/faqmain.component';
import { HomeonemainComponent } from './eduman/homeone/homeonemain/homeonemain.component';
import { HomethreemainComponent } from './eduman/homethree/homethreemain/homethreemain.component';
import { HometwomainComponent } from './eduman/hometwo/hometwomain/hometwomain.component';
import { BecomeinstructormainComponent } from './eduman/instructor/becomeinstructor/becomeinstructormain/becomeinstructormain.component';
import { InstructormainComponent } from './eduman/instructor/instructormain/instructormain.component';
import { InstructorpromainComponent } from './eduman/instructor/instructorpromain/instructorpromain.component';
import { MembershipmainComponent } from './eduman/membership/membershipmain/membershipmain.component';
import { ShopmainComponent } from './eduman/shop/shopmain/shopmain.component';
import { ShopdetailsmainComponent } from './eduman/shopdetails/shopdetailsmain/shopdetailsmain.component';
import { WishlistmainComponent } from './eduman/wishlist/wishlistmain/wishlistmain.component';
import { ZoomclassdetailsmainComponent } from './eduman/zoom/zoomclassdetailsmain/zoomclassdetailsmain.component';
import { ZoomclassmainComponent } from './eduman/zoom/zoomclassmain/zoomclassmain.component';


const routes: Routes = [ 
  { path: '', component:  HometwomainComponent, pathMatch: 'full' },
  {
    path: 'home-2',
    component: HomeonemainComponent
  },
  {
    path: 'home-3',
    component: HomethreemainComponent
  },
  {
    path: 'about',
    component: AboutmainComponent
  },
  {
    path: 'membership',
    component: MembershipmainComponent
  },
  {
    path: '404-page',
    component: ErrorpageComponent
  },
  {
    path: 'contact',
    component: ContactmainComponent
  },
  {
    path: 'faq-page',
    component: FaqmainComponent
  },
  {
    path: 'faq-details',
    component: FaqdetailsmainComponent
  },
  {
    path: 'event',
    component: EventmainComponent
  },
  {
    path: 'event-details',
    component: EventdetailsmainComponent
  },
  {
    path: 'instructor',
    component: InstructormainComponent
  },
  {
    path: 'instructor-profile',
    component: InstructorpromainComponent
  },
  {
    path: 'become-instructor',
    component: BecomeinstructormainComponent
  },
  {
    path: 'blog',
    component: BlogmainComponent
  },
  {
    path: 'blog-details',
    component: BlogdetailsmainComponent
  },
  {
    path: 'wishlist',
    component: WishlistmainComponent
  },
  {
    path: 'cart',
    component: CartmainComponent
  },
  {
    path: 'checkout',
    component: CheckoutmainComponent
  },
  {
    path: 'shop',
    component: ShopmainComponent
  },
  {
    path: 'shop-details',
    component: ShopdetailsmainComponent
  },
  {
    path: 'course',
    component: CourseonemainComponent
  },
  {
    path: 'course-2',
    component: CoursetwomainComponent
  },
  {
    path: 'course-3',
    component: CoursethreemainComponent
  },
  {
    path: 'course-4',
    component: CoursefourmainComponent
  },
  {
    path: 'course-details/:id',
    component: CoursedetailsmainComponent
  },
  {
    path: 'zoom-class',
    component: ZoomclassmainComponent
  },
  {
    path: 'zoom-class-detalis',
    component: ZoomclassdetailsmainComponent
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
