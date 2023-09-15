/// <reference types="@angular/localize" />

import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';

import 'globalthis/auto';
// In your main.ts or index.ts file
(window as any).global = window;


platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));


