import { Component } from '@angular/core';
import { AbstractPageContainerComponent } from '../abstract/abstract-page-container/abstract-page-container.component';
import { Review } from 'src/app/model/review';
import { ReviewService } from 'src/app/services/review.service';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse } from '@angular/common/http';
@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css']
})
export class ReviewComponent extends AbstractPageContainerComponent{
  public reviews: Review[];
  public editReview: Review;
  public deleteReview: Review;



  constructor(private toastr : ToastrService,private reviewService : ReviewService, router : Router, activatedRoute : ActivatedRoute)
  {
    super(router, activatedRoute);
  }

  override ngOnInit(): void {
    super.ngOnInit();
    this.getReviews();
  }

  public getReviews() : void {
    this.reviewService.getAllReviews(this.pageNumber, this.pageSize).subscribe(
      response => {
        this.reviews = response.content;
        this.totalPages = response.totalPages;
        this.currentPage = this.pageNumber;
        this.currentSize = this.pageSize;
      }
    );
  }

  public override pageUpdate(): void {
    this.reviewService.getAllReviews(this.pageNumber,this.pageSize).subscribe(
      result => {
        this.reviews = result.content;
        this.totalPages = result.totalPages;
        this.currentPage = this.pageNumber;
        this.currentSize = this.pageSize;
      }
    );
  }

  public onOpenModal(review : Review, mode : string) : void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'add') {
      button.setAttribute('data-target', '#addReviewModal');
    }
    if (mode === 'delete') {
      this.deleteReview = review;
      button.setAttribute('data-target', '#deleteReviewModal');
    }
    if (mode === 'edit') {
      this.editReview = review;
      button.setAttribute('data-target', '#updateReviewModal');
    }
    container.appendChild(button);
    button.click();
  }

  public onAddReview(addForm : NgForm) : void {
    document.getElementById('add-review-form').click();
    this.reviewService.addReview(addForm.value).subscribe(
      (response : Review) => {
        console.log(response);
        this.toastr.success("Review added successfully!");
        this.getReviews();
        addForm.reset();

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

  public onUpdateReview(review : Review) : void {
    this.reviewService.updateReview(review).subscribe(
      (response : Review) => {
        console.log(response);
        this.toastr.success("Review updated successfully!");
        this.getReviews();
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

  public onDeleteReview(reviewId : number) : void {
    this.reviewService.deleteReview(reviewId).subscribe(
      (response : void) => {
        console.log(response);
        this.toastr.success("Review deleted successfully!");
        this.getReviews();
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
}
