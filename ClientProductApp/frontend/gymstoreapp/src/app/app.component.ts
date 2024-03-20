import { Component, OnInit } from '@angular/core';
import { Product } from './model/product';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ProductService } from './services/product.service';

import { Entry } from './model/Entry';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  constructor(private productService : ProductService) { }

  ngOnInit(): void {
   
  }




}
