import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Customer } from 'src/app/model/customer';
import { CustomerService } from 'src/app/services/customer.service';
import { NgForm } from '@angular/forms';
import { AbstractPageContainerComponent } from '../abstract/abstract-page-container/abstract-page-container.component';
import { HttpErrorResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css']
})
export class CustomerComponent extends AbstractPageContainerComponent{
  public customers : Customer[];
  public editCustomer : Customer;
  public deleteCustomer : Customer;
  public stats : {id : number, firstName: string,lastName: string, likes  : number }[];

  public statsCurrentPage : number = 1;
  public statsTotalPages : number = 0;

  constructor(private toastr : ToastrService,private customerService :CustomerService, router : Router, activatedRoute : ActivatedRoute) {
    super(router,activatedRoute);
  }

  override ngOnInit() : void {
    super.ngOnInit();
    this.getCustomers();
  }

  public getCustomers() : void {
    this.customerService.getAllCustomers(this.pageNumber, this.pageSize).subscribe(
      response => {
        this.customers = response.content;
        this.totalPages = response.totalPages;
        this.currentPage = this.pageNumber;
        this.currentSize = this.pageSize;
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

  public onOpenModal(customer : Customer, mode : string) : void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addCustomerModal');
    }
    if (mode === 'delete') {
      this.deleteCustomer = customer;
      button.setAttribute('data-target', '#deleteCustomerModal');
    }
    if (mode === 'edit') {
      this.editCustomer = customer;
      button.setAttribute('data-target', '#updateCustomerModal');
    }
    if (mode === 'stats')
    {
      button.setAttribute('data-target', '#statisticalReportModal');
    }
    container.appendChild(button);
    button.click();
  }

  public getStatisticalRep() : void {
   this.customerService.getStatisticalReportCustomer(this.statsCurrentPage,10).subscribe(
    (response) => {
      this.stats = response.content.sort((a,b) => a.likes - b.likes);
      this.statsTotalPages = response.totalPages;
    }
   );
  }

  public onAddCustomer(addForm : NgForm) : void {
    document.getElementById('add-customer-form').click();
    this.customerService.addCustomer(addForm.value).subscribe(
      (response : Customer) => {
        console.log(response);
        this.getCustomers();
        addForm.reset();
        this.toastr.success("Customer added successfully!");
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

  public onUpdateCustomer(customer : Customer) : void {
    this.customerService.updateCustomer(customer).subscribe(
      (response : Customer) => {
        console.log(response);
        this.getCustomers();
        this.toastr.success("Customer updated successfully!");
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

  public onDeleteCustomer(customerId : number) : void {
    this.customerService.deleteCustomer(customerId).subscribe(
      (response : void) => {
        console.log(response);
        this.getCustomers();
        this.toastr.success("Customer deleted successfully!");
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

  public changePageStats(pageNumber : number) : void {
    pageNumber = Math.max(pageNumber, 0);
    pageNumber = Math.min(pageNumber, this.statsTotalPages - 1);
    this.statsCurrentPage = pageNumber;
    this.getStatisticalRep();
  }

  public override pageUpdate(): void {
    this.customerService.getAllCustomers(this.pageNumber,this.pageSize).subscribe(
      result => {
        this.customers = result.content;
        this.totalPages = result.totalPages;
        this.currentPage = this.pageNumber;
        this.currentSize = this.pageSize;
      }
      
    );
  }
}
