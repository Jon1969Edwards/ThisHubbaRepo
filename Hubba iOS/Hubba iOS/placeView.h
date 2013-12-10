//
//  placeView.h
//  Hubba iOS
//
//  Created by Jackson on 11/24/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//


@protocol senddataProtocol <NSObject>

-(void)sendDataBack:(NSArray *)array; //I am thinking my data is NSArray , you can use another object for store your information.

@end

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

@property (nonatomic) UILabel *helpName;
@property (nonatomic) UILabel *helpDist;
@property (nonatomic) UILabel *helpDiff;
@property (nonatomic) UILabel *helpBust;

// delegate to pass back location to view on map
@property(nonatomic,assign)id delegate;

@property (retain, nonatomic) IBOutlet UIImageView *placeImage;
@property (nonatomic) IBOutlet UILabel *placeName;
@property (nonatomic) IBOutlet UILabel *bustLevel;
@property (nonatomic) IBOutlet UILabel *diffLevel;
@property (nonatomic) IBOutlet UILabel *dist;
@property (nonatomic) IBOutlet UICollectionView *picList;

@property (retain, nonatomic) NSData *imgData;
@property (retain, nonatomic) UIImageView *fullScreenImage;

- (IBAction)addImage:(id)sender;
- (IBAction)done:(id)sender;
- (IBAction)viewOnMap:(id)sender;

-(UIImage *)scaleImage:(UIImage *)image toSize:(CGSize)newSize;

@end
