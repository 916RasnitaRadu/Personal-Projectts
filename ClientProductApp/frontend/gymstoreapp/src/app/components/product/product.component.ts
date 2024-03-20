import { Component } from '@angular/core';
import { Product } from 'src/app/model/product';
import { ProductService } from 'src/app/services/product.service';
import { ActivatedRoute, ResolveStart, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { AbstractPageContainerComponent } from '../abstract/abstract-page-container/abstract-page-container.component';
import { HttpErrorResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent extends AbstractPageContainerComponent{
  public products : Product[];
  public editProduct : Product;
  public deleteProduct : Product;
  public stats :{id : number, name: string, likes  : number }[];

  public price: string;

  public statsCurrentPage : number = 1;
  public statsTotalPages : number = 0;

  constructor(private toastr : ToastrService,private productService : ProductService, router : Router, activatedRoute : ActivatedRoute) { 
    super(router,activatedRoute);
  }

  override ngOnInit() : void {
    super.ngOnInit();
    this.getProducts();
  }

  public onOpenModal(product: Product, mode : string) : void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addProductModal');
    }
    if (mode === 'delete') {
      this.deleteProduct = product;
      button.setAttribute('data-target', '#deleteProductModal');
    }
    if (mode === 'edit') {
      this.editProduct = product;
      button.setAttribute('data-target', '#updateProductModal');
    }
    if (mode === 'stats')
    {
      button.setAttribute('data-target', '#statisticalReportModal');
    }
    container.appendChild(button);
    button.click();
  }

  public getProducts() : void {
    this.productService.getAllProducts(this.currentPage, 10).subscribe(
      response => {
        this.products = response.content;
        this.totalPages = response.totalPages;
        this.currentPage = this.pageNumber;
        this.currentSize = this.pageSize;
      },

    );
  }
  
  public onAddProduct(addForm : NgForm) : void {
    document.getElementById('add-product-form').click();
    this.productService.addProduct(addForm.value).subscribe(
      (response : Product) => {
        console.log(response);
        this.getProducts();
        addForm.reset();
        this.toastr.success("Product added successfully!");
      },
      (error : HttpErrorResponse) => {
        for (const property in error.error)
        {
          const message = error.error[property];
          this.toastr.error(message, "An error ocurred!");
        }
      }
      );
  }

  public onUpdateProduct(product : Product) : void {
    this.productService.updateProduct(product).subscribe(
      (response : Product) => {
        console.log(response);
        this.getProducts();
        this.toastr.success("Product updated successfully!");
      },
      (error : HttpErrorResponse) => {
        for (const property in error.error)
        {
          const message = error.error[property];
          this.toastr.error(message, "An error ocurred!");
        }
      }
    );
  }

  public onDeleteProduct(productId : number) : void {
    this.productService.deleteProduct(productId).subscribe(
      (response : void) => {
        console.log(response);
        this.getProducts();
        this.toastr.success("Product deleted successfully!");
      },
      (error : HttpErrorResponse) => {
        for (const property in error.error)
        {
          const message = error.error[property];
          this.toastr.error(message, "An error ocurred!");
        }
      }
    );
  }

  public filterProducts() : void {
    console.log('here', this.price);
    console.log(typeof this.price);
  
    if (isNaN(+this.price)) {
      this.toastr.error("Please insert a valid number!");
    }

    this.productService.filterProductsByPrice(+this.price,this.pageNumber,this.pageSize).subscribe(
      (response) => {
        this.products = response.content;
        this.totalPages = response.totalPages;
        this.currentPage = this.pageNumber;
        this.currentSize = this.pageSize;
      }
    );
  }

  public getStatisticalRep() : void {
    this.productService.getStatisticalReportProduct(this.statsCurrentPage,10).subscribe(
      (response) => {
        this.stats = response.content.sort((a, b) => a.likes - b.likes);
        this.statsTotalPages = response.totalPages;
        console.log('hasdas', this.stats)
      }
    );
  }

  public sortProductsByPrice() : void {
    this.products = this.products.sort((a, b) => a.price - b.price);
  }

  public changePageStats(pageNumber : number) : void {
    pageNumber = Math.max(pageNumber, 0);
    pageNumber = Math.min(pageNumber, this.statsTotalPages - 1);
    this.statsCurrentPage = pageNumber;
    this.getStatisticalRep();
  }

  public override pageUpdate(): void {
    if (+this.price > 0)
    {
      this.filterProducts();
    }
    else {
      this.getProducts();
    }
  }
}
