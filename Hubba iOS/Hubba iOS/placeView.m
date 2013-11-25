//
//  placeView.m
//  Hubba iOS
//
//  Created by Jackson on 11/24/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import "placeView.h"

@interface placeView () <UICollectionViewDataSource, UICollectionViewDelegate>

@end

@implementation placeView

// ----------------------------------------------------------------
// UICollectionView Stuff
- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
  return 1;
}
- (UICollectionViewCell *)collectionView:(UICollectionView *)collectionView cellForItemAtIndexPath:(NSIndexPath *)indexPath{
  static NSString *identifier = @"Cell";
  
  UICollectionViewCell *cell = [collectionView dequeueReusableCellWithReuseIdentifier:identifier forIndexPath:indexPath];
  
  UIImageView *recipeImageView = (UIImageView *)[cell viewWithTag:100];
//  recipeImageView.image = [UIImage imageNamed:[recipeImages objectAtIndex:indexPath.row]];
  
  return cell;
}
// ----------------------------------------------------------------


- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
  
    CAGradientLayer *gradient = [CAGradientLayer layer];
    [gradient setFrame:CGRectMake(0, 5, 104, 40)];
    [gradient setColors:[NSArray arrayWithObjects:(id)[UIColor blackColor].CGColor, (id)GRAY2.CGColor, (id)[UIColor blackColor].CGColor, nil]];
    [but1.layer insertSublayer:gradient atIndex:0];
    gradient = [CAGradientLayer layer];
    [gradient setFrame:CGRectMake(0, 5, 104, 40)];
    [gradient setColors:[NSArray arrayWithObjects:(id)[UIColor blackColor].CGColor, (id)GRAY2.CGColor, (id)[UIColor blackColor].CGColor, nil]];
    [but2.layer insertSublayer:gradient atIndex:0];
    gradient = [CAGradientLayer layer];
    [gradient setFrame:CGRectMake(0, 5, 104, 40)];
    [gradient setColors:[NSArray arrayWithObjects:(id)[UIColor blackColor].CGColor, (id)GRAY2.CGColor, (id)[UIColor blackColor].CGColor, nil]];
    [but3.layer insertSublayer:gradient atIndex:0];
    gradient = [CAGradientLayer layer];
    [gradient setFrame:CGRectMake(0, 0, 280, 40)];
    [gradient setColors:[NSArray arrayWithObjects:(id)[UIColor blackColor].CGColor, (id)GRAY2.CGColor, (id)[UIColor blackColor].CGColor, nil]];
    [but4.layer insertSublayer:gradient atIndex:0];
  
  [favorites setContentHorizontalAlignment:UIControlContentHorizontalAlignmentCenter];
  [favorites.titleLabel setTextAlignment:NSTextAlignmentCenter];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
