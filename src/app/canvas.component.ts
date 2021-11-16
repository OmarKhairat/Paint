import {
    Component, Input, ElementRef, AfterViewInit, ViewChild
  } from '@angular/core';
  import { fromEvent } from 'rxjs';
  import { switchMap, takeUntil, pairwise } from 'rxjs/operators'

 
  