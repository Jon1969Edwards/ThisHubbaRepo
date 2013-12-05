//
//  TableCell.m
//  Hubba iOS
//
//  Created by Jackson on 12/3/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import "TableCell.h"

@implementation TableCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
  
  // set background color.
  [self setBackgroundColor:[UIColor clearColor]];
  
  // the cells image.
  self.pic = [[UIImageView alloc] initWithFrame:CGRectMake(20, 2, 80, 80)];
  [self.pic setBackgroundColor:[UIColor clearColor]];
  [self.pic setContentMode:UIViewContentModeScaleAspectFit];
  [self.pic setImage:[UIImage imageNamed:@"default"]];
  [self.pic.layer setCornerRadius:10];
  [self.pic setClipsToBounds:YES];
  
  // the constant title labels of the cell.
  UILabel *Distance = [[UILabel alloc] initWithFrame:CGRectMake(111, 31, 59, 21)];
  UILabel *BustLevel = [[UILabel alloc] initWithFrame:CGRectMake(172, 23, 62, 37)];
  UILabel *DiffLevel = [[UILabel alloc] initWithFrame:CGRectMake(238, 23, 62, 37)];
  [Distance setText:@"Distance:"];
  [BustLevel setText:@"Bust Level:"];
  [DiffLevel setText:@"Difficulty Level:"];
  [Distance setTextAlignment:NSTextAlignmentCenter];
  [BustLevel setTextAlignment:NSTextAlignmentCenter];
  [DiffLevel setTextAlignment:NSTextAlignmentCenter];
  [Distance setLineBreakMode:NSLineBreakByWordWrapping];
  [BustLevel setLineBreakMode:NSLineBreakByWordWrapping];
  [DiffLevel setLineBreakMode:NSLineBreakByWordWrapping];
  [Distance setNumberOfLines:2];
  [BustLevel setNumberOfLines:2];
  [DiffLevel setNumberOfLines:2];
  [Distance setTextColor:[UIColor whiteColor]];
  [BustLevel setTextColor:[UIColor whiteColor]];
  [DiffLevel setTextColor:[UIColor whiteColor]];
  [Distance setFont:[UIFont fontWithName:@"GeezaPro" size:14]];
  [BustLevel setFont:[UIFont fontWithName:@"GeezaPro" size:14]];
  [DiffLevel setFont:[UIFont fontWithName:@"GeezaPro" size:14]];
  
  // the variable objects being created for the cell.
  self.name = [[UILabel alloc] initWithFrame:CGRectMake(110, 2, 200, 21)];
  self.dist = [[UILabel alloc] initWithFrame:CGRectMake(115, 60, 42, 21)];
  self.bust = [[UILabel alloc] initWithFrame:CGRectMake(184, 60, 42, 21)];
  self.diff = [[UILabel alloc] initWithFrame:CGRectMake(250, 60, 42, 21)];
  [self.dist setTextAlignment:NSTextAlignmentCenter];
  [self.bust setTextAlignment:NSTextAlignmentCenter];
  [self.diff setTextAlignment:NSTextAlignmentCenter];
  [self.name setText:@"Name"];
  [self.dist setText:@"0.0"];
  [self.bust setText:@"0.0"];
  [self.diff setText:@"0.0"];
  [self.name setTextColor:[UIColor whiteColor]];
  [self.dist setTextColor:[UIColor whiteColor]];
  [self.diff setTextColor:[UIColor whiteColor]];
  [self.bust setTextColor:[UIColor whiteColor]];
  [self.name setFont:[UIFont fontWithName:@"GeezaPro-Bold" size:18]];
  [self.dist setFont:[UIFont fontWithName:@"GeezaPro" size:14]];
  [self.diff setFont:[UIFont fontWithName:@"GeezaPro" size:14]];
  [self.bust setFont:[UIFont fontWithName:@"GeezaPro" size:14]];

  // add objects to cell.
  [self.contentView addSubview:self.pic];
  
  [self.contentView addSubview:Distance];
  [self.contentView addSubview:DiffLevel];
  [self.contentView addSubview:BustLevel];
  
  [self.contentView addSubview:self.name];
  [self.contentView addSubview:self.diff];
  [self.contentView addSubview:self.dist];
  [self.contentView addSubview:self.bust];

  return self;
}

-(void) setInfo:(NSString*)name dist:(NSString*)dist diff:(NSString*)diff bust:(NSString*)bust img:(UIImage*)img
{
  [self.pic setImage:img];
  [self.name setText:name];
  [self.dist setText:dist];
  [self.diff setText:diff];
  [self.bust setText:bust];
  
}
- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}

@end
