import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductComponent } from './components/product/product.component';
import { HomeComponent } from './components/home/home.component';
import { CustomerComponent } from './components/customer/customer.component';
import { ReviewComponent } from './components/review/review.component';

const routes: Routes = [
    {
        path: '',
        component: HomeComponent
    },
    {
        path: "product",
        component: ProductComponent
    },
    {
        path: "customer",
        component: CustomerComponent
    },
    {
        path:"review",
        component: ReviewComponent
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {}