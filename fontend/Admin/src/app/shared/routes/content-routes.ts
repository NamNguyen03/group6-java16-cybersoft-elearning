import { Routes } from '@angular/router';

export const content: Routes = [
  {
    path: 'dashboard',
    loadChildren: () => import('../../components/dashboard/dashboard.module').then(m => m.DashboardModule),
  },
  {
    path: 'programs',
    loadChildren: () => import('../../components/program/program.module').then(m => m.ProgramModule),
    data: {
      breadcrumb: "Programs"
    }
  },
  {
    path: 'courses',
    loadChildren: () => import('../../components/course/courses.module').then(m => m.CoursesModule),
    data: {
      breadcrumb: "Courses"
    }
  },
  {
    path: 'roles',
    loadChildren: () => import('../../components/roles/roles.module').then(m => m.RolesModule),
    data: {
      breadcrumb: "Roles"
    }
  },
  {
    path: 'groups',
    loadChildren: () => import('../../components/group/group.module').then(m => m.GroupModule),
    data: {
      breadcrumb: "Groups"
    }
  },
  {
    path: 'users',
    loadChildren: () => import('../../components/users/users.module').then(m => m.UsersModule),
    data: {
      breadcrumb: "Users"
    }
  },
  {
    path: 'lessons',
    loadChildren: () => import('../../components/lesson/lessons.module').then(m => m.LessonsModule),
    data: {
      breadcrumb: "Lessons"
    }
  },
  {
    path: 'comments',
    loadChildren: () => import('../../components/comment/comment.module').then(m => m.CommentModule),
    data: {
      breadcrumb: "Comments"
    }
  },
  {
    path: 'settings',
    loadChildren: () => import('../../components/setting/setting.module').then(m => m.SettingModule),
    data: {
      breadcrumb: "Settings"
    }
  }

];