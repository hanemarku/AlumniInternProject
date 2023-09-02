import { Component, OnInit } from '@angular/core';
import { Post } from './post';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { PostService } from '../services/post.service';
import { HttpParams } from '@angular/common/http';
import {  data, error, post } from 'jquery';
import { UserLogin } from '../services/authenication-service/authentication.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.sass']
})
export class PostComponent implements OnInit{

  postForm: any;
  theId!: string;
  npost!: Post;
  content!: string;
  profilPicUrl!:string;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private postService: PostService
  ){
    this.postForm = this.formBuilder.group({
      'content': ['', [Validators.required]],
      'profilPicUrl': [''] 
    });

    

    route.params
    .subscribe((params: Params) => this.theId = params['postId']);
  }

  ngOnInit(): void {
    if(this.theId){
      this.postService.getPost(this.theId)
      .subscribe((data: any) =>{
        this.npost = data;

      }, error =>{

      }
      )
    }

  }

  postFormSubmit(content:string, profilPicUrl: string) {
    content;
    profilPicUrl;

    this.postService.createPost().subscribe(post => 
      data = post;
      )
  }
  
  //this.postService.deletePost(this.theId).subscribe();

  //this.postService.updatePost(this.theId, this.postForm).subscribe();
  

  createPost(){
    if(this.postForm.valid){
       let fPost: any  = {
        content: this.postForm.get('content').value,
        profilPicUrl: this.postForm.get('profilPicUrl').value
      };

      this.postService.createPost(fPost).subscribe(
        (response: any) => {
          console.log('Post created succesfully:', response);
        },
        (error: any) => {
          console.error('Error creating post: ', error);
        }
      );
    }
  }

  updatePost(postId:string, post:Post){
    let cont = this.postForm.value;
    this.postService.updatePost(postId, cont)
    .subscribe(
      data => {
    }
    )
  }


  onSubmit(post:any){
  }
}