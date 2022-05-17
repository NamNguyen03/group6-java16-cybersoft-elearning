import { Routes } from '@angular/router';

export const content: Routes = [
  {
    path: 'dashboard',
    loadChildren: () => import('../../components/dashboard/dashboard.module').then(m => m.DashboardModule),
  },
  {
    path: 'products',
    loadChildren: () => import('../../components/products/products.module').then(m => m.ProductsModule),
    data: {
      breadcrumb: "Products"
    }
  },
  {
    path: 'sales',
    loadChildren: () => import('../../components/sales/sales.module').then(m => m.SalesModule),
    data: {
      breadcrumb: "Sales"
    }
  },
  {
    path: 'program',
    loadChildren: () => import('../../components/program/program.module').then(m => m.ProgramModule),
    data: {
      breadcrumb: "Program"
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
    path: 'media',
    loadChildren: () => import('../../components/media/media.module').then(m => m.MediaModule),
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
    path: 'localization',
    loadChildren: () => import('../../components/localization/localization.module').then(m => m.LocalizationModule),
    data: {
      breadcrumb: "Localization"
    }
  },
  {
    path: 'reports',
    loadChildren: () => import('../../components/reports/reports.module').then(m => m.ReportsModule),
  },
  {
    path: 'settings',
    loadChildren: () => import('../../components/setting/setting.module').then(m => m.SettingModule),
    data: {
      breadcrumb: "Settings"
    }
  },
  {
    path: 'invoice',
    loadChildren: () => import('../../components/invoice/invoice.module').then(m => m.InvoiceModule),
    data: {
      breadcrumb: "Invoice"
    }
  }
];