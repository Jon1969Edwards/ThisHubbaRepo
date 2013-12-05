//
//  TableCell.h
//  Hubba iOS
//
//  Created by Jackson on 12/3/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "Constant.h"

@interface TableCell : UITableViewCell

@property (retain, nonatomic) IBOutlet UIImageView *pic;
@property (retain, nonatomic) IBOutlet UILabel *name;
@property (retain, nonatomic) IBOutlet UILabel *dist;
@property (retain, nonatomic) IBOutlet UILabel *bust;
@property (retain, nonatomic) IBOutlet UILabel *diff;


@end
