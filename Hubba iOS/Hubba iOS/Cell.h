//
//  Cell.h
//  Hubba iOS
//
//  Created by Jackson on 11/25/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Constant.h"

@interface Cell : UICollectionViewCell

@property (retain, nonatomic) IBOutlet UIImageView *pic;


-(void) setImg:(UIImage *)image;

@end
