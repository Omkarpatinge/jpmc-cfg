# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
from django.conf import settings


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
    ]

    operations = [
        migrations.CreateModel(
            name='Appointment',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('location', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('datetime', models.DateTimeField()),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='AppointmentChat',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('sender', models.IntegerField(choices=[(1, b'Mentor'), (2, b'Mentee')])),
                ('text', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('appointment', models.ForeignKey(to='KatalystApp.Appointment')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Feedback',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('rating', models.IntegerField(max_length=3)),
                ('text', models.CharField(max_length=1024)),
                ('appointment', models.ForeignKey(to='KatalystApp.Appointment')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Goals',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('name', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('description', models.CharField(default=None, max_length=1024, null=True, blank=True)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Mentee',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('first_name', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('last_name', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('mobile', models.CharField(default=None, max_length=10, null=True, blank=True)),
                ('email', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('gender', models.IntegerField(choices=[(1, b'Male'), (2, b'Female')])),
                ('age', models.IntegerField(default=None, max_length=4, null=True, blank=True)),
                ('location', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('gcm', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('uuid', models.CharField(default=None, max_length=512, null=True, blank=True)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Mentor',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('first_name', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('last_name', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('email', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('mobile', models.CharField(default=None, max_length=10, null=True, blank=True)),
                ('gender', models.IntegerField(choices=[(1, b'Male'), (2, b'Female')])),
                ('age', models.IntegerField(default=None, max_length=4, null=True, blank=True)),
                ('location', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('max_num_of_students', models.IntegerField(max_length=1)),
                ('occupation', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('gcm', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('uuid', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('django_user', models.OneToOneField(to=settings.AUTH_USER_MODEL)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Qualification',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('institute', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('degree', models.CharField(default=None, max_length=512, null=True, blank=True)),
                ('start_date', models.IntegerField(default=None, max_length=5, null=True, blank=True)),
                ('end_date', models.IntegerField(default=None, max_length=5, null=True, blank=True)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Reminders',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('datetime', models.DateTimeField()),
                ('for_appointment', models.ForeignKey(to='KatalystApp.Appointment')),
                ('mentee', models.ForeignKey(to='KatalystApp.Mentee')),
                ('mentor', models.ForeignKey(to='KatalystApp.Mentor')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Review',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('rating', models.IntegerField(max_length=3)),
                ('text', models.CharField(max_length=1024)),
                ('mentee', models.ForeignKey(to='KatalystApp.Mentee')),
                ('mentor', models.ForeignKey(to='KatalystApp.Mentor')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.AddField(
            model_name='mentor',
            name='qualifications',
            field=models.ManyToManyField(to='KatalystApp.Qualification'),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='mentee',
            name='assigned_mentor',
            field=models.ForeignKey(to='KatalystApp.Mentor'),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='mentee',
            name='django_user',
            field=models.OneToOneField(to=settings.AUTH_USER_MODEL),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='appointment',
            name='goals',
            field=models.ManyToManyField(to='KatalystApp.Goals'),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='appointment',
            name='mentee',
            field=models.ForeignKey(to='KatalystApp.Mentee'),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='appointment',
            name='mentor',
            field=models.ForeignKey(to='KatalystApp.Mentor'),
            preserve_default=True,
        ),
    ]
