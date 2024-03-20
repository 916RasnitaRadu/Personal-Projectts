import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { GenericPageDTO } from '../model/genericPageDTO';
import { Observable, tap } from 'rxjs';
import { Customer } from '../model/customer';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root'})
export class CustomerService {
    private apiServerUrl = environment.apiBaseUrl;
    
    constructor(private http : HttpClient) { }

    public getAllCustomers(pageNumber: number, pageSize : number): Observable<GenericPageDTO<Customer>> {
        return this.http.get<GenericPageDTO<Customer>>(`${this.apiServerUrl}/customer/all` + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
      }
    
      public getCustomer(customerId : number): Observable<Customer> {
        return this.http.get<Customer>(`${this.apiServerUrl}/customer/${customerId}`);
      }
    
      public addCustomer(customer : Customer): Observable<Customer> {
        return this.http.post<Customer>(`${this.apiServerUrl}/customer/`, customer);
      }
    
      public updateCustomer(customer : Customer ): Observable<Customer> {
        return this.http.put<Customer>(`${this.apiServerUrl}/customer/`, customer);
      }
    
      public deleteCustomer(customerId : number): Observable<void> {
        return this.http.delete<void>(`${this.apiServerUrl}/customer/${customerId}`);
      }

      public getStatisticalReportCustomer(pageNumber : number, pageSize :number) : Observable<GenericPageDTO<{id : number, firstName: string,lastName : string, likes  : number }>> {
        return this.http.get<GenericPageDTO<{id : number, firstName: string,lastName : string, likes  : number }>>(`${this.apiServerUrl}/review/statCust` + `?pageNumber=${pageNumber}`+  `&pageSize=${[pageSize]}`);
      }
}