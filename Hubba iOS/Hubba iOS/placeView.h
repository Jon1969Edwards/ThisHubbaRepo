//
//  placeView.h
//  Hubba iOS
//
//  Created by Jackson on 11/24/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <objc/runtime.h>
#import "Constant.h"
#import "Cell.h"

@interface placeView : UIViewController{
  IBOutlet UIView *but0;
  IBOutlet UIView *but1;
  IBOutlet UIView *but2;
  IBOutlet UIView *but3;
  IBOutlet UIView *but4;
  
  IBOutlet UIButton *favorites;
  IBOutlet UIButton *viewonmap;

  NSArray *objects;
  CGRect origFrame;
  int rowShowing;
}

@property (retain, nonatomic) IBOutlet UIImageView *placeImage;
@property (retain, nonatomic) IBOutlet UILabel *placeName;
@property (retain, nonatomic) IBOutlet UILabel *bustLevel;
@property (retain, nonatomic) IBOutlet UILabel *diffLevel;
@property (retain, nonatomic) IBOutlet UICollectionView *picList;

@property (retain, nonatomic) NSData *imgData;
@property (retain, nonatomic) UIImageView *fullScreenImage;

- (IBAction)addImage:(id)sender;
- (IBAction)done:(id)sender;

-(UIImage *)scaleImage:(UIImage *)image toSize:(CGSize)newSize;

@end
