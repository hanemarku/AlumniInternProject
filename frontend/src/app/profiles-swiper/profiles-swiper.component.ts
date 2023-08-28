import { AfterViewInit, Component, ElementRef, Input, ViewChild } from '@angular/core';
// import Swiper from 'swiper';
// import * as bootstrap from 'bootstrap';

@Component({
  selector: 'app-profiles-swiper',
  templateUrl: './profiles-swiper.component.html',
  styleUrls: ['./profiles-swiper.component.sass']
})
export class ProfilesSwiperComponent {

//   @ViewChild('multipleCardCarousel') carouselElement!: ElementRef;
//   carousel: any;
  
//   @ViewChild('carouselInner') carouselInner!: ElementRef;

//   cardWidth: number = 0;
//   scrollPosition: number = 0;

//   ngAfterViewInit(): void {
//     this.carousel = new bootstrap.Carousel(this.carouselElement.nativeElement, {
//       interval: false,
//       wrap: false
//     });
//     this.cardWidth = this.carouselInner.nativeElement.querySelector('.carousel-item').clientWidth;
//   }

//   onNextClick(): void {
//     const carouselWidth = this.carouselInner.nativeElement.scrollWidth;
//     if (this.scrollPosition < carouselWidth - this.cardWidth * 4) {
//       this.scrollPosition += this.cardWidth;
//       this.carouselInner.nativeElement.scrollTo({
//         left: this.scrollPosition,
//         behavior: 'smooth'
//       });
//     }
//   }

//   onPrevClick(): void {
//     if (this.scrollPosition > 0) {
//       this.scrollPosition -= this.cardWidth;
//       this.carouselInner.nativeElement.scrollTo({
//         left: this.scrollPosition,
//         behavior: 'smooth'
//       });
//     }
//   }
}




