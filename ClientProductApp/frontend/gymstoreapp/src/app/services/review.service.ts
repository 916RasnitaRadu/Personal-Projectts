import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GenericPageDTO } from '../model/genericPageDTO';
import { Review } from '../model/review';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root'})
export class ReviewService {
//   private apiServerUrl = 'http://104.197.134.216:80';
    private apiServerUrl = environment.apiBaseUrl;

    constructor(private http: HttpClient) {}

    public getAllReviews(pageNumber: number, pageSize : number) : Observable<GenericPageDTO<Review>> {
        return this.http.get<GenericPageDTO<Review>>(`${this.apiServerUrl}/review/all` + `?pageNumber=${pageNumber}` + `&pageSize=${[pageSize]}`);
    }

    public addReview(review : Review): Observable<Review> {
        return this.http.post<Review>(`${this.apiServerUrl}/review/`, review);
    }

    public updateReview(review : Review ): Observable<Review> {
        return this.http.put<Review>(`${this.apiServerUrl}/review/`, review);
    }
    
    public deleteReview(reviewId : number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/review/${reviewId}`);
    }
}